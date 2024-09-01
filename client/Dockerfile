FROM node:18-alpine
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
COPY package.docker.json ./package.json
EXPOSE 3000
ENTRYPOINT ["npm", "start"]