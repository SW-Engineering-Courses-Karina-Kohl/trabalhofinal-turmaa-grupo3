Rails.application.config.middleware.insert_before 0, Rack::Cors do
  allow do
    origins "*"   # tighten to "http://localhost:5173" for local or your prod domain

    resource "*",
      headers: :any,
      methods: %i[get post put patch delete options head],
      expose:  %w[Authorization]
  end
end
