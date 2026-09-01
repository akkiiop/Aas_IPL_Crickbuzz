import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
    plugins: [react()],

    server: {
        proxy: {
            "/api": {
                target: "http://32.197.3.108",
                changeOrigin: true
            }
        }
    }
});