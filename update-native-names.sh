#!/bin/bash

# Script to update all Firebase plugin native package names to use Capgo prefix
# This fixes installation issues in Capacitor apps

set -e

# Colors
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

echo -e "${GREEN}Updating native package names for all Firebase plugins${NC}"
echo ""

update_plugin() {
  plugin_folder=$1
  plugin_camel=$2
  plugin_dir="packages/$plugin_folder"

  echo -e "${YELLOW}Processing: $plugin_folder (Firebase$plugin_camel)${NC}"

  if [ ! -d "$plugin_dir" ]; then
    echo "  ⚠️  Directory not found: $plugin_dir"
    return
  fi

  cd "$plugin_dir"

  # Old names
  OLD_PODSPEC="CapacitorFirebase${plugin_camel}.podspec"
  OLD_POD_NAME="CapacitorFirebase${plugin_camel}"
  OLD_SWIFT_NAME="CapacitorFirebase${plugin_camel}"
  OLD_PLUGIN_TARGET="Firebase${plugin_camel}Plugin"
  OLD_TEST_TARGET="Firebase${plugin_camel}PluginTests"

  # New names
  NEW_PODSPEC="CapgoCapacitorFirebase${plugin_camel}.podspec"
  NEW_POD_NAME="CapgoCapacitorFirebase${plugin_camel}"
  NEW_SWIFT_NAME="CapgoCapacitorFirebase${plugin_camel}"
  NEW_PLUGIN_TARGET="CapgoFirebase${plugin_camel}Plugin"
  NEW_TEST_TARGET="CapgoFirebase${plugin_camel}PluginTests"

  # 1. Rename and update .podspec file
  if [ -f "$OLD_PODSPEC" ]; then
    echo "  ✓ Updating $OLD_PODSPEC"
    sed -i '' "s/s.name = '${OLD_POD_NAME}'/s.name = '${NEW_POD_NAME}'/" "$OLD_PODSPEC"
    mv "$OLD_PODSPEC" "$NEW_PODSPEC"
  elif [ -f "$NEW_PODSPEC" ]; then
    echo "  ℹ️  $NEW_PODSPEC already exists, updating content"
    sed -i '' "s/s.name = '${OLD_POD_NAME}'/s.name = '${NEW_POD_NAME}'/" "$NEW_PODSPEC" 2>/dev/null || true
  else
    echo "  ⚠️  No podspec file found"
  fi

  # 2. Update Package.swift
  if [ -f "Package.swift" ]; then
    echo "  ✓ Updating Package.swift"
    sed -i '' "s/name: \"${OLD_SWIFT_NAME}\"/name: \"${NEW_SWIFT_NAME}\"/g" Package.swift
    sed -i '' "s/name: \"${OLD_PLUGIN_TARGET}\"/name: \"${NEW_PLUGIN_TARGET}\"/g" Package.swift
    sed -i '' "s/name: \"${OLD_TEST_TARGET}\"/name: \"${NEW_TEST_TARGET}\"/g" Package.swift
    sed -i '' "s/\"${OLD_PLUGIN_TARGET}\"/\"${NEW_PLUGIN_TARGET}\"/g" Package.swift
  else
    echo "  ⚠️  No Package.swift found"
  fi

  # 3. Update package.json
  if [ -f "package.json" ]; then
    echo "  ✓ Updating package.json"
    # Update files array
    sed -i '' "s/\"${OLD_PODSPEC}\"/\"${NEW_PODSPEC}\"/" package.json
    # Update verify:ios script
    sed -i '' "s/-scheme ${OLD_POD_NAME}/-scheme ${NEW_POD_NAME}/" package.json
    # Update docgen script
    sed -i '' "s/--api ${OLD_PLUGIN_TARGET}/--api ${NEW_PLUGIN_TARGET}/" package.json
  else
    echo "  ⚠️  No package.json found"
  fi

  cd ../..
  echo "  ✅ Done"
  echo ""
}

# Process each plugin
update_plugin "analytics" "Analytics"
update_plugin "app" "App"
update_plugin "app-check" "AppCheck"
update_plugin "authentication" "Authentication"
update_plugin "crashlytics" "Crashlytics"
update_plugin "firestore" "Firestore"
update_plugin "functions" "Functions"
update_plugin "messaging" "Messaging"
update_plugin "performance" "Performance"
update_plugin "remote-config" "RemoteConfig"
update_plugin "storage" "Storage"

echo -e "${GREEN}All plugins updated!${NC}"
