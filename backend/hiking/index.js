const express = require("express");
const http = require("http");
const app = express();

const PORT = process.env.PORT || 3000;
const eurekaHelper = require("./eureka-helper");

const server = http.createServer(app);

server.listen(PORT, () => {
  console.log(`Server listening at PORT ` + PORT);
});

app.get("/", (req, res) => {
  res.json("I am user-service");
});

eurekaHelper.registerWithEureka("hiking-share-service", PORT);

// http Server를 socket.io 서버로 업그레이드
const io = require("socket.io")(server);

io.sockets.on("connection", (socket) => {
  console.log(`Socket connected : ${socket.id}`);

  socket.on("enter", (data) => {
    const locationData = JSON.parse(data);
    const memberId = locationData.memberId;
    const meetupId = locationData.meetupId;

    socket.join(`${meetupId}`); // 소모임 일정 참여

    console.log(`Member #${memberId} joined Meetup #${meetupId}`);

    socket.broadcast.to(`${meetupId}`).emit("update", JSON.stringify(locationData));
  });

  socket.on("newLocation", (data) => {
    const locationData = JSON.parse(data);
    const memberId = locationData.memberId;
    const meetupId = locationData.meetupId;

    console.log(`Member #${memberId} shared location to Meetup #${meetupId}`);
    socket.broadcast.to(`${meetupId}`).emit("update", JSON.stringify(locationData));
  });

  socket.on("disconnect", () => {
    console.log(`Socket disconnected : ${socket.id}`);
  });
});

// To close the server:
server.close(() => {
  console.log("Server has been closed.");
});
