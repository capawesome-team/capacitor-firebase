#!/bin/bash

# Script to build and publish all Capacitor Firebase plugins
# Usage: ./publish-all-plugins.sh

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}======================================${NC}"
echo -e "${GREEN}Publishing All Capacitor Firebase Plugins${NC}"
echo -e "${GREEN}======================================${NC}"
echo ""

# Array of all plugins
PLUGINS=(
  "analytics"
  "app"
  "app-check"
  "authentication"
  "crashlytics"
  "firestore"
  "functions"
  "messaging"
  "performance"
  "remote-config"
  "storage"
)

# Check if npm token is set
if [ -z "$NPM_TOKEN" ]; then
  echo -e "${YELLOW}Warning: NPM_TOKEN environment variable not set.${NC}"
  echo -e "${YELLOW}You may need to login with 'npm login' or set NPM_TOKEN${NC}"
  echo ""
fi

# Install root dependencies first
echo -e "${YELLOW}Installing root dependencies...${NC}"
bun i
echo ""

# Counter for successful/failed publishes
SUCCESS_COUNT=0
FAIL_COUNT=0
FAILED_PLUGINS=()

# Process each plugin
for PLUGIN in "${PLUGINS[@]}"; do
  echo -e "${GREEN}======================================${NC}"
  echo -e "${GREEN}Processing: $PLUGIN${NC}"
  echo -e "${GREEN}======================================${NC}"

  cd "packages/$PLUGIN"

  # Build the plugin
  echo -e "${YELLOW}Building $PLUGIN...${NC}"
  if bun run build; then
    echo -e "${GREEN}✓ Build successful${NC}"
  else
    echo -e "${RED}✗ Build failed for $PLUGIN${NC}"
    FAIL_COUNT=$((FAIL_COUNT + 1))
    FAILED_PLUGINS+=("$PLUGIN (build)")
    cd ../..
    continue
  fi

  # Publish to npm
  echo -e "${YELLOW}Publishing $PLUGIN to npm...${NC}"
  if npm publish --access public; then
    echo -e "${GREEN}✓ Published successfully${NC}"
    SUCCESS_COUNT=$((SUCCESS_COUNT + 1))
  else
    echo -e "${RED}✗ Publish failed for $PLUGIN${NC}"
    FAIL_COUNT=$((FAIL_COUNT + 1))
    FAILED_PLUGINS+=("$PLUGIN (publish)")
  fi

  cd ../..
  echo ""
done

# Summary
echo -e "${GREEN}======================================${NC}"
echo -e "${GREEN}Summary${NC}"
echo -e "${GREEN}======================================${NC}"
echo -e "${GREEN}Successfully published: $SUCCESS_COUNT/${#PLUGINS[@]}${NC}"

if [ $FAIL_COUNT -gt 0 ]; then
  echo -e "${RED}Failed: $FAIL_COUNT${NC}"
  echo -e "${RED}Failed plugins:${NC}"
  for FAILED in "${FAILED_PLUGINS[@]}"; do
    echo -e "${RED}  - $FAILED${NC}"
  done
  exit 1
else
  echo -e "${GREEN}All plugins published successfully! 🎉${NC}"
fi
