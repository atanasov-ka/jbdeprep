FROM node:10.15.3-alpine

RUN mkdir -p /vb-frontend
WORKDIR /vb-frontend

COPY package*.json ./
RUN npm install
RUN npm install -g serve

COPY ./ ./

EXPOSE 3000
CMD serve -s build -l 3000