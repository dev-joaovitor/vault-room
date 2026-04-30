FROM node:24-slim AS base
WORKDIR /app

COPY package*.json ./
RUN npm ci
COPY . .

FROM base AS development
EXPOSE 5173

CMD ["npm", "run", "dev"]
