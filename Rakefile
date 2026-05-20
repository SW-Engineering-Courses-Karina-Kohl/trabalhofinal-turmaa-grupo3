require 'dotenv/load'

# Rakefile — SalesOps Admin orchestration
# Requirements: ruby, docker, docker compose

APP_ENVIRONMENT = ENV["APP_ENV"]
FRONTEND_DIRECTORY = ENV["FRONTEND_DIR"]
BACKEND_DIRECTORY = ENV["BACKEND_DIR"]
PSEUDOBACKEND_DIRECTORY = ENV["PSEUDOBACKEND_DIR"]

# ── Docker ────────────────────────────────────────────────────────────────────
namespace :services do
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
    svc = ENV["DOCKER_LOG_SERVICE"] || ""
    sh "docker compose logs -f #{svc}"
  end

  desc "Rebuild and restart everything from scratch"
  task restart: [:down, :build, :up]
end

# ── Frontend (local, no Docker) ───────────────────────────────────────────────
desc "Install frontend deps and start local frontend dev console"
task frontend: %w[frontend:install frontend:dev]

namespace :frontend do
  desc "Install frontend dependencies"
  task :install do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm install" }
  end

  desc "Open the Vite dev console (local)"
  task :dev do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm run dev" }
  end

  desc "Build frontend for production"
  task :build do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm run build" }
  end

  desc "Run frontend tests (Vitest)"
  task :test do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm run test" }
  end

  desc "Run tests with coverage"
  task :coverage do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm run coverage" }
  end

  desc "Lint frontend code"
  task :lint do
    Dir.chdir(FRONTEND_DIRECTORY) { sh "npm run lint" }
  end
end


