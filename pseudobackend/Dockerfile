# syntax = docker/dockerfile:1

ARG RUBY_VERSION=4.0.1
FROM ruby:${RUBY_VERSION}-slim

# Install system dependencies
RUN apt-get update -qq && \
    apt-get install --no-install-recommends -y \
    build-essential \
    libpq-dev \
    libyaml-dev \
    postgresql-client \
    nodejs \
    git \
    && rm -rf /var/lib/apt/lists/* /var/cache/apt/*

# Create a non-root user (good practice)
RUN useradd --create-home --shell /bin/bash rails
USER rails

# Set working directory
WORKDIR /rails

# Install gems
COPY --chown=rails:rails Gemfile Gemfile.lock ./
RUN bundle install

# Copy the rest of the application (this will be overridden by volume in dev)
COPY --chown=rails:rails . .

EXPOSE 3000

# Default command (will be overridden in docker-compose)
CMD ["bundle", "exec", "rails", "server", "-b", "0.0.0.0"]
