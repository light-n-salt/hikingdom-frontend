FROM node:18.14.2-slim

RUN mkdir /usr/src/app
WORKDIR /usr/src/app
COPY ./package* /usr/src/app/

RUN npm install
RUN npm install -g serve

COPY . /usr/src/app
RUN npm run build

EXPOSE 3000

ENTRYPOINT ["serve", "-s", "build"]
