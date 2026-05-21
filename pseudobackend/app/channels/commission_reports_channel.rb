class CommissionReportsChannel < ApplicationCable::Channel
  def subscribed
    stream_from "commission_reports"
  end

  def unsubscribed
    stop_all_streams
  end
end
