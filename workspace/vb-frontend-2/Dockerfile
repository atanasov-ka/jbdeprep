FROM node:10.15.3-alpine

COPY . /app

RUN cd /app \
&& npm install --save

EXPOSE 3000

CMD ["node", "/app/bin/www"]