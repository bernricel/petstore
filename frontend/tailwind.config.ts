import type { Config } from "tailwindcss";

export default {
  content: ["./index.html", "./src/**/*.{ts,tsx}"],
  theme: {
    extend: {
      colors: {
        sand: "#f4efe8",
        forest: "#174536",
        coral: "#de7f54",
        ink: "#1f2933",
      },
    },
  },
  plugins: [],
} satisfies Config;

