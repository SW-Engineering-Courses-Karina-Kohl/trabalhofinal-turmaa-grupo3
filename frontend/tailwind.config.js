/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{ts,tsx}"],
  theme: {
    extend: {
      colors: {
        brand: {
          50:  "#f0faf4",
          100: "#dcf4e6",
          200: "#bbe8ce",
          300: "#88d5ae",
          400: "#4dba86",
          500: "#289e69",
          600: "#1a7f53",
          700: "#166644",
          800: "#145238",
          900: "#11432f",
          950: "#08261b",
        },
      },
      fontFamily: {
        sans: ['"DM Sans"', "sans-serif"],
        mono: ['"JetBrains Mono"', "monospace"],
      },
    },
  },
  plugins: [],
};
