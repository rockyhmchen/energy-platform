import type { NextConfig } from "next";

const BACK_END_API_URL = process.env.NEXT_PUBLIC_BACK_END_API_URL || "http://localhost:8080";

const nextConfig: NextConfig = {
  proxy: {
    '/api/': process.env.NEXT_PUBLIC_BACK_END_API_URL || 'http://localhost:8080/'
  },
  async rewrites() {
    return [
      {
        source: "/api/:path*",
        destination: BACK_END_API_URL + "/:path*" // Proxy to Backend
      }
    ]
  }
};

export default nextConfig;
