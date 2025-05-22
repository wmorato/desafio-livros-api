FROM node:18 AS build-stage
WORKDIR /app
COPY package*.json ./
COPY .env.production ./
COPY vite.config.js ./
COPY index.html ./
COPY src ./src
COPY public ./public
COPY nginx.conf /etc/nginx/conf.d/default.conf

RUN npm install
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
