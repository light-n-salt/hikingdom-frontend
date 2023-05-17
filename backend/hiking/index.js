const express = require("express");
const http = require("http");
const app = express();
const eurekaHelper = require("./eureka-helper");

const PORT = process.env.PORT || 3000; // port 번호 지정
const server = http.createServer(app); // http 서버 생성
eurekaHelper.registerWithEureka("hiking-share-service", PORT); // eureka 서버에 등록

server.listen(PORT, () => {
  console.log(`Server listening at PORT ` + PORT);
});

// prevent caching
app.use((req, res, next) => {
  res.set("Cache-Control", "no-store");
  next();
});

app.get("/", (req, res) => {
  res.json("hiking location share service for hikingdom");
});

// http server를 socket.io 서버로 업그레이드
const io = require("socket.io")(server);

io.sockets.on("connection", (socket) => {
  console.log(`Socket connected : ${socket.id}`);

  // 1) 입장 시, 동작
  socket.on("enter", (data) => {
    // 데이터 추출
    const enterData = JSON.parse(data);
    const memberId = enterData.memberId;
    const meetupId = enterData.meetupId;
    console.log(`Member #${memberId} joined Meetup #${meetupId}`);

    socket.join(`${meetupId}`); // 해당 소켓을 meetupId에 연결

    socket.broadcast.to(`${meetupId}`).emit("enter", JSON.stringify(enterData));
  });

  // 2) 위치 공유 시, 동작
  socket.on("newLocation", (data) => {
    const locationData = JSON.parse(data);
    const memberId = locationData.memberId;
    const meetupId = locationData.meetupId;
    console.log(`Member #${memberId} shared location to Meetup #${meetupId}`);

    socket.broadcast.to(`${meetupId}`).emit("newLocation", JSON.stringify(locationData));
  });

  // 3) 연결 해제 시, 동작
  socket.on("leave", (data) => {
    const leaveData = JSON.parse(data);
    const memberId = leaveData.memberId;
    const meetupId = leaveData.meetupId;
    console.log(`Member #${memberId} left Meetup #${meetupId}`);

    socket.leave(`${meetupId}`); // 해당 소켓을 meetupId에서 해제
  });
});
