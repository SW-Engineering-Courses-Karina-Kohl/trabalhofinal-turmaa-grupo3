require 'dotenv/load'

# Rakefile — SalesOps Admin orchestration
# Requirements: ruby, docker, docker compose

APP_ENVIRONMENT = ENV["APP_ENV"]
FRONTEND_DIRECTORY = ENV["FRONTEND_DIR"]
BACKEND_DIRECTORY = ENV["BACKEND_DIR"]
PSEUDOBACKEND_DIRECTORY = ENV["PSEUDOBACKEND_DIR"]
PSEUDOBACKEND_WS_ENDPOINT = ENV["PSEUDOBACKEND_WS_ENDPOINT"]
PSEUDOBACKEND_API_ENDPOINT = ENV["PSEUDOBACKEND_API_ENDPOINT"]
PSEUDOBACKEND_SWAGGER_DOC = ENV["PSEUDOBACKEND_SWAGGER_DOC"]

ENV["API_ENDPOINT"] = PSEUDOBACKEND_API_ENDPOINT
ENV["WS_ENDPOINT"] = PSEUDOBACKEND_WS_ENDPOINT
ENV["SWAGGER_DOC"] = "/#{PSEUDOBACKEND_DIRECTORY}/#{PSEUDOBACKEND_SWAGGER_DOC}"

DOCKER_COMPOSE_FILES = "-f docker-compose.yml -f docker-compose.pseudo.yml"

puts ENV["SWAGGER_DOC"]

# ── Docker ────────────────────────────────────────────────────────────────────
namespace :services do
  desc "Build all Docker images"
  task :build do
    sh "docker compose #{DOCKER_COMPOSE_FILES} build"
  end

  desc "Start all services (detached)"
  task :up do
    sh "docker compose #{DOCKER_COMPOSE_FILES} up -d"
  end

  desc "Stop all services"
  task :down do
    sh "docker compose #{DOCKER_COMPOSE_FILES} down"
  end

  desc "Tail logs for all services (or pass SERVICE=frontend)"
  task :logs do
    svc = ENV["DOCKER_LOG_SERVICE"] || ""
    sh "docker compose #{DOCKER_COMPOSE_FILES} logs -f #{svc}"
  end

  desc "Update"
  task :update do
    sh "docker compose #{DOCKER_COMPOSE_FILES} run backend bundle install"
  end
  
  desc "Console"
  task :console do
    sh "docker compose #{DOCKER_COMPOSE_FILES} run backend bash"
  end

  desc "Console Swagger"
  task :console_swagger do
    sh "docker compose #{DOCKER_COMPOSE_FILES} run swagger-ui /bin/ash"
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


