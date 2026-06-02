Rails.application.routes.draw do
  # ── Swagger Endpoint ───────────────────────────────────────────────────────
  mount Rswag::Api::Engine => '/api-docs'
  
  # ── Avo admin ──────────────────────────────────────────────────────────────
  mount Avo::Engine, at: Avo.configuration.root_path

  # ── ActionCable ────────────────────────────────────────────────────────────
  mount ActionCable.server => "/cable"

  # ── API v1 ─────────────────────────────────────────────────────────────────
  namespace :api do
    namespace :v1 do
      # Upload endpoint (accepts CSV, kicks off job)
      resources :uploads, only: :create

      # Commission batches
      resources :commissions, controller: "commission_reports", only: %i[index show] do
        member do
          get :sellers
          get :export
        end
      end
    end
  end

  # Health check
  get "/up", to: proc { [200, {}, ["OK"]] }
end
