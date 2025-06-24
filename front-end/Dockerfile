FROM node:22-alpine AS base

FROM base AS builder

WORKDIR /app

COPY package.json package-lock.json* ./
RUN npm install


FROM base AS runner
WORKDIR /app
COPY --from=builder /app/node_modules ./node_modules
COPY . .

EXPOSE 3000
ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

CMD ["npm", "run", "dev"]
