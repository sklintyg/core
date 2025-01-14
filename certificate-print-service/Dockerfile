ARG from_image
FROM $from_image

ARG from_image
ARG project_name
ARG artifact
ARG artifact_name
ARG artifact_version
ARG vcs_url
ARG vcs_ref

LABEL se.inera.from_image=${from_image}       \
      se.inera.project=${project_name}        \
      se.inera.artifact=${artifact}   \
      se.inera.artifact_name=${artifact_name} \
      se.inera.version=${artifact_version}    \
      se.inera.vcs_url=${vcs_url}             \
      se.inera.vcs_ref=${vcs_ref}

ENV APP_NAME=$artifact
ENV SCRIPT_DEBUG=true

USER root

# Install necessary dependencies
RUN apt-get update && apt-get install -y \
    fonts-liberation \
    wget \
    gnupg \
    ca-certificates \
    --no-install-recommends
    #npm \

#RUN sh -c npx playwright install --with-deps --only-shell
# Add Google's official GPG key
RUN wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -

# Set up the stable repository
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'

# Install Chromium and other necessary packages
#RUN apt-get update && apt-get install -y \
    google-chrome-stable \
    --no-install-recommends

# Clean up apt-get
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Set environment variable to use headless mode
ENV CHROME_BIN=/usr/bin/google-chrome
ENV CHROME_PATH=/usr/bin/google-chrome


ADD /app/build/libs/app.jar /deployments

USER nobody
