require_relative "boot"
require "rails/all"

Bundler.require(*Rails.groups)

module SalesopsBackend
  class Application < Rails::Application
    config.load_defaults 8.0

    config.api_mode = false   # We need full stack for Avo + ActionCable
    config.active_job.queue_adapter = :solid_queue

    config.action_cable.cable = { adapter: "redis", url: ENV.fetch("REDIS_URL", "redis://localhost:6379/1") }

    config.autoload_paths += [Rails.root.join("app/avo")]
  end
end
