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

RUN apt-get install libglib2.0-0 \
libnss3 \
libnspr4 \
libdbus-1-3 \
libatk1.0-0 \
libatk-bridge2.0-0 \
libcups2 \
libdrm2 \
libx11-6 \
libxcomposite1 \
libxdamage1 \
libxext6 \
libxfixes3 \
libxrandr2 \
libgbm1 \
libxcb1 \
libxkbcommon0 \
libpango-1.0-0 \
libcairo2 \
libasound2 \
libatspi2.0-0

ADD /app/build/libs/app.jar /deployments
