require "ostruct"

Avo.configure do |config|
  config.root_path           = "/"
  config.app_name            = "SalesOps Admin"
  config.license             = "community"
  config.current_user_method do 
    OpenStruct.new(id: 1, name: "Admin")
  end

  config.set_initial_breadcrumbs do
    add_breadcrumb "SalesOps", "/"
  end
end
