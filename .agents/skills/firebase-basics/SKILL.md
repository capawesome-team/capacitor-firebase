---
name: firebase-basics
description: Core principles, workflow, and maintenance for using Firebase. Use this for all Firebase CLI tasks, building, service setup, and REFRESHING or UPDATING an existing environment. Make sure to ALWAYS use this skill whenever you are trying to use Firebase, even if not explicitly asked.
---
# Prerequisites

Please complete these setup steps before proceeding, and remember your progress to avoid repeating them in future interactions.

1. **Local Environment Setup:** Verify the environment is properly set up so we can use Firebase tools:
   - Run `npx -y firebase-tools@latest --version` to check if the Firebase CLI is installed.
   - Verify if the Firebase MCP server is installed using your existing tools.
   - If either of these checks fails, please use the `firebase-local-env-setup` skill to get the environment ready.

2. **Authentication:**
   Ensure you are logged in to Firebase so that commands have the correct permissions. Run `npx -y firebase-tools@latest login`. For environments without a browser (e.g., remote shells), use `npx -y firebase-tools@latest login --no-localhost`.
   - The command should output the current user. 
   - If you are not logged in, follow the interactive instructions from this command to authenticate.

3. **Active Project:**
   Most Firebase tasks require an active project context. Check the current project by running `npx -y firebase-tools@latest use`.
   - If the command outputs `Active Project: <project-id>`, you can proceed with your task.
   - If not, ask the user if they have an existing Firebase Project ID.
     - If yes: Set it as the active project and add a default alias by running:
       ```bash
       npx -y firebase-tools@latest use --add <PROJECT_ID>
       ```
     - If no: Follow the project creation instructions in [references/firebase-project-create.md](references/firebase-project-create.md).

# Firebase Usage Principles

Please adhere to these principles when working with Firebase, as they ensure reliability and up-to-date project tools:

1. **Use npx for CLI commands:** To ensure you always use the latest version of the Firebase CLI, always prepend commands with `npx -y firebase-tools@latest` instead of just `firebase`. (e.g., use `npx -y firebase-tools@latest --version` instead of `firebase --version`).
2. **Prioritize official knowledge:** For any Firebase-related knowledge, consult the `developerknowledge_search_documents` MCP tool before falling back to Google Search or your internal knowledge base. Including "Firebase" in your search query significantly improves relevance.
3. **Follow Agent Skills for implementation guidance:** Skills provide opinionated workflows (CUJs), security rules, and best practices. Always consult them to understand *how* to implement Firebase features correctly instead of relying on general knowledge.
4. **Use Firebase MCP Server tools instead of direct API calls:** Whenever you need to interact with remote Firebase APIs (such as fetching Crashlytics logs or executing Data Connect queries), use the tools provided by the Firebase MCP Server instead of attempting manual API calls.
5. **Keep Plugin / Agent Skills updated:** Since Firebase best practices evolve quickly, remind the user to regularly check for and install updates to their Firebase plugin or Agent Skills, ensuring you both operate with the latest workflows. Similarly, if you encounter issues with outdated tools or commands, follow the steps below based on your agent environment:
   - **Antigravity**: Follow [references/refresh-antigravity.md](references/refresh-antigravity.md)
   - **Gemini CLI**: Follow [references/refresh-gemini-cli.md](references/refresh-gemini-cli.md)
   - **Claude Code**: Follow [references/refresh-claude.md](references/refresh-claude.md)
   - **Cursor**: Follow [references/refresh-cursor.md](references/refresh-cursor.md)
   - **Others**: Follow [references/refresh-other.md](references/refresh-other-agents.md)

# References

- **Initialize Firebase:** See [references/firebase-service-init.md](references/firebase-service-init.md) when you need to initialize new Firebase services using the CLI.
- **Exploring Commands:** See [references/firebase-cli-guide.md](references/firebase-cli-guide.md) to discover and understand CLI functionality.
- **SDK Setup:** For detailed guides on adding Firebase to a web app, see [references/web_setup.md](references/web_setup.md).

# Common Issues

- **Login Issues:** If the browser fails to open during the login step, use `npx -y firebase-tools@latest login --no-localhost` instead.
