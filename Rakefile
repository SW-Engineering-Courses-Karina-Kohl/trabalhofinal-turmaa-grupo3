# Rakefile — SalesOps Admin orchestration
# Requirements: ruby, docker, docker compose

FRONTEND_DIR = "frontend"

# ── Docker ────────────────────────────────────────────────────────────────────

desc "Build all Docker images"
task :build do
  sh "docker compose build"
end

desc "Start all services (detached)"
task :up do
  sh "docker compose up -d"
end

desc "Stop all services"
task :down do
  sh "docker compose down"
end

desc "Tail logs for all services (or pass SERVICE=frontend)"
task :logs do
  svc = ENV["SERVICE"] || ""
  sh "docker compose logs -f #{svc}"
end

desc "Rebuild and restart everything from scratch"
task restart: [:down, :build, :up]

# ── Frontend (local, no Docker) ───────────────────────────────────────────────

desc "Install frontend dependencies"
task :install do
  Dir.chdir(FRONTEND_DIR) { sh "npm install" }
end

desc "Open the Vite dev console (local)"
task :dev do
  Dir.chdir(FRONTEND_DIR) { sh "npm run dev" }
end

desc "Build frontend for production"
task :build_frontend do
  Dir.chdir(FRONTEND_DIR) { sh "npm run build" }
end

desc "Run frontend tests (Vitest)"
task :test do
  Dir.chdir(FRONTEND_DIR) { sh "npm run test" }
end

desc "Run tests with coverage"
task :coverage do
  Dir.chdir(FRONTEND_DIR) { sh "npm run coverage" }
end

desc "Lint frontend code"
task :lint do
  Dir.chdir(FRONTEND_DIR) { sh "npm run lint" }
end

# ── Default ───────────────────────────────────────────────────────────────────

desc "Install deps and start local dev console (one command)"
task default: [:install, :dev]
