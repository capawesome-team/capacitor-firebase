#!/bin/bash

# Script to migrate Android package names from io.capawesome to app.capgo
# This updates package declarations and moves files to the new directory structure

set -e

GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}Migrating Android packages from io.capawesome to app.capgo${NC}"
echo ""

# Plugin mapping: folder-name -> plugin-name
declare -a PLUGINS=(
  "analytics:analytics"
  "app:app"
  "app-check:appcheck"
  "authentication:authentication"
  "crashlytics:crashlytics"
  "firestore:firestore"
  "functions:functions"
  "messaging:messaging"
  "performance:performance"
  "remote-config:remoteconfig"
  "storage:storage"
)

for plugin_mapping in "${PLUGINS[@]}"; do
  IFS=':' read -r folder_name plugin_name <<< "$plugin_mapping"
  plugin_dir="packages/$folder_name"

  echo -e "${YELLOW}Processing: $folder_name${NC}"

  if [ ! -d "$plugin_dir/android" ]; then
    echo "  ⚠️  No android directory found"
    continue
  fi

  cd "$plugin_dir"

  OLD_PACKAGE_PATH="android/src/main/java/io/capawesome/capacitorjs/plugins/firebase/$plugin_name"
  NEW_PACKAGE_PATH="android/src/main/java/app/capgo/capacitor/firebase/$plugin_name"

  OLD_PACKAGE="io.capawesome.capacitorjs.plugins.firebase.$plugin_name"
  NEW_PACKAGE="app.capgo.capacitor.firebase.$plugin_name"

  # 1. Create new package directory structure
  if [ -d "$OLD_PACKAGE_PATH" ]; then
    echo "  ✓ Creating new package structure"
    mkdir -p "$NEW_PACKAGE_PATH"

    # 2. Move all Java/Kotlin files to new location
    echo "  ✓ Moving source files"
    find "$OLD_PACKAGE_PATH" -type f \( -name "*.java" -o -name "*.kt" \) -exec cp {} "$NEW_PACKAGE_PATH/" \;

    # 3. Update package declarations in all moved files
    echo "  ✓ Updating package declarations"
    find "$NEW_PACKAGE_PATH" -type f \( -name "*.java" -o -name "*.kt" \) -exec sed -i '' "s/package $OLD_PACKAGE/package $NEW_PACKAGE/g" {} \;

    # 4. Update imports in all files
    echo "  ✓ Updating import statements"
    find "android/src" -type f \( -name "*.java" -o -name "*.kt" \) -exec sed -i '' "s/import $OLD_PACKAGE\\./import $NEW_PACKAGE./g" {} \;

    # 5. Remove old directory structure
    echo "  ✓ Removing old package structure"
    rm -rf "android/src/main/java/io"

  else
    echo "  ℹ️  Package path not found: $OLD_PACKAGE_PATH"
  fi

  cd ../..
  echo "  ✅ Done"
  echo ""
done

echo -e "${GREEN}Android package migration complete!${NC}"
echo ""
echo -e "${YELLOW}Next steps:${NC}"
echo "1. Test builds: bun run verify:android"
echo "2. Check for any remaining references to io.capawesome"
