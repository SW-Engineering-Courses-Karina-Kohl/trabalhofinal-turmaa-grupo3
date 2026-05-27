# frozen_string_literal: true

require 'dotenv/load'
require "ostruct"
require 'algorithms'
require 'psych'

APP_ENVIRONMENT = ENV["APP_ENV"]

## Testes de valores individuais
def development?
  return APP_ENVIRONMENT == "development"
end

def production?
  return APP_ENVIRONMENT == "production"
end

def pseudobackend?
  return ENV["BACKEND_TYPE"] == "pseudo"
end

def normal_backend?
  ENV["BACKEND_TYPE"] == "normal"
end

## Validações
(raise "Invalid APP_ENVIRONMENT") unless development? || production?
(raise "Invalid BACKEND_TYPE") unless pseudobackend? || normal_backend?

# Restrições
(raise "Pseudobackend cannot be run in production mode!") if production? && pseudobackend?

## Setup dos arquivos docker-compose
files = Containers::PriorityQueue.new

def files.generate()
  strs = []
  while size() > 0
    strs << " -f #{pop()}"
  end
  return strs.join
end

if development?
  files.push("docker-compose.dev.yml", 10)
  if pseudobackend?
    files.push("docker-compose.dev.pseudo.yml", 9)
  end
else
  files.push("docker-compose.prod.yml", 10)
end

docker_files = files.generate()

services_configuration = Psych.load_file("services_configuration.yml")
backend_configuration = if pseudobackend? then services_configuration['backend']['pseudo'] ;else services_configuration['backend']['normal'] ;end

FRONTEND_DIRECTORY = services_configuration['frontend']['directory']
BACKEND_DIRECTORY = backend_configuration['directory']

ENV['BACKEND_DIR'] = BACKEND_DIRECTORY
ENV['API_ENDPOINT'] = backend_configuration['api_endpoint']
ENV['WS_ENDPOINT'] = backend_configuration['ws_endpoint']
ENV['SWAGGER_DOC'] = backend_configuration['swagger_doc']

# ── Utility ────────────────────────────────────────────────────────────────────
def detached_shell(cmd, env = {})
  pid = Process.spawn(env, "xterm", "-e", "bash", "-c", "#{cmd};exec bash")
  Process.detach(pid)
end

# ── Docker ────────────────────────────────────────────────────────────────────
namespace :services do
  desc "Build all Docker images"
  task build: %w[build:backend] do
    sh "docker compose #{docker_files} build"
  end

  desc "Start all services"
  task up: %w[operations:run_normal_backend_watcher] do
    flags = if production?
              " -d "
            elsif normal_backend?
              "  "
            else
              ""
            end
    detached_shell "docker compose #{docker_files} up #{flags}", {
      'APP_ENV' => APP_ENVIRONMENT,
      'BACKEND_DIR' => backend_configuration['directory'],
      'API_ENDPOINT' => backend_configuration['api_endpoint'],
      'WS_ENDPOINT' => backend_configuration['ws_endpoint'],
      'SWAGGER_DOC' => backend_configuration['swagger_doc']
    }
  end

  desc "Stop all services"
  task :down do
    sh "docker compose #{docker_files} down"
  end

  desc "Tail logs for all services (or pass SERVICE=frontend)"
  task :logs do
    service = ENV["DOCKER_LOG_SERVICE"] || ""
    sh "docker compose #{docker_files} logs -f #{service}"
  end

  desc "Rebuild and restart everything from scratch"
  task restart: [:down, :build, :up]
end

namespace :console do
  task :backend do
    sh "docker compose #{docker_files} run backend bash"
  end

  task :swagger do
    sh "docker compose #{docker_files} run swagger-ui /bin/ash"
  end
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

namespace :build do
  desc "Build backend container"
  task backend: %w[operations:build_backend operations:maven_dependencies_update operations:pseudobackend_bundle operations:pseudobackend_migrate]
end


desc "Separated operations for Rake internal use"
namespace :operations do
  task :run_normal_backend_watcher do
    if normal_backend?
      Dir.chdir(BACKEND_DIRECTORY) do
        if development?
          detached_shell("trap 'exit 0' INT\nwhile true; do find src -name \"*.java\" | entr -d -r mvn compile -Pdev -DskipTests; done")
        else
          detached_shell("mvn clean package -Pprod -DskipTests")
        end
      end
    end
  end

  task :maven_dependencies_update do
    if normal_backend?
      Dir.chdir(BACKEND_DIRECTORY) do
        sh 'mvn compile dependency:copy-dependencies -DoutputDirectory=target/dependency'
      end
    end
  end
  task :build_backend do
    sh "docker compose #{docker_files} build backend"
  end

  task :pseudobackend_bundle do
    if pseudobackend?
      sh "docker compose #{docker_files} run backend bundle install"
    end
  end

  task pseudobackend_migrate: %w[operations:build_backend operations:pseudobackend_bundle] do
    if pseudobackend?
      sh "docker compose #{docker_files} run backend rails db:drop db:create db:migrate db:seed"
    end
  end
end
