# 🎮 Captcha Notifier

A Minecraft Fabric mod that sends Discord notifications when captchas appear and helps automate chat mini-games.

> **⚠️ Proof of Concept**  
> A technical demonstration of Fabric mod development and Discord webhook integration, built for the OPBlocks server environment. Shows event-driven automation and custom UI implementation in Minecraft. **Not intended for production use** - automation typically violates server rules. Created as a portfolio project.

## ✨ What It Does

- 🔔 **Discord Alerts** - Sends webhook notifications when captchas pop up in-game
- ⚡ **Chat Game Automation** - Responds to "first to type" and math challenges
- 🎉 **Vote Party Helper** - Auto-runs `/vp` when vote parties happen
- ⏱️ **Human-like Timing** - Customizable delays to mimic natural responses
- ⚙️ **Easy Configuration** - Press `K` in-game to adjust settings

## 📋 Requirements

- Minecraft **1.21.x**
- Fabric Loader
- Fabric API

## 🚀 Getting Started

### Installation
1. Build the mod from source (instructions below)
2. Drop the `.jar` from `build/libs/` into your `.minecraft/mods` folder
3. Launch Minecraft with Fabric installed

### Setup
Press **K** in-game to open settings and configure:

- **Discord Webhook** - Paste your webhook URL for notifications
- **User ID** - Add your Discord ID to get pinged
- **Features** - Toggle AutoType, VoteParty, and webhook notifications
- **Timing** - Adjust response delays (min/max in milliseconds)

## 🔨 Building from Source

**What you need:**
- JDK 21+
- Git

**Build it:**
```bash
# Clone the repo
git clone https://github.com/GibsonHF/captcha-notifier.git
cd captcha-notifier

# Build with Gradle
./gradlew build

# Your mod JAR will be in build/libs/
```

## 💡 How to Use

### Setting Up Discord Notifications
1. Go to your Discord server settings → Integrations → Webhooks
2. Create a new webhook and copy the URL
3. In Minecraft, press **K** and paste the URL
4. Hit save - you'll now get Discord alerts when captchas appear!

### Auto-Type for Mini-Games
Enable AutoType in the config menu, and the mod will handle:
- **Reaction challenges** - "First to type [word] in chat wins"
- **Math problems** - "MATH » 5 + 3 = ?"

The timing is randomized to look more natural.

### Vote Party Auto-Claim
Turn on VoteParty mode to automatically run `/vp` when vote parties trigger.

## ⚙️ Configuration

Settings are saved in `.minecraft/config/captcha-notifier.json`. You can edit this manually or use the in-game menu.

```json
{
  "webhookUrl": "https://discord.com/api/webhooks/...",
  "discordUserId": "123456789",
  "webhookEnabled": true,
  "pingEnabled": false,
  "autoTypeEnabled": false,
  "votePartyEnabled": false,
  "reactionMinDelay": 1000,
  "reactionMaxDelay": 1800,
  "mathMinDelay": 1500,
  "mathMaxDelay": 2700
}
```

Delays are in milliseconds - adjust them to control how quickly the mod responds to challenges.

## 📜 License & Disclaimer

**MIT License** - Feel free to learn from this code! See [LICENSE.txt](LICENSE.txt).

**Important:** This is a proof of concept project showcasing Fabric mod development and Discord webhook integration, designed around the OPBlocks server environment. **This is not intended for actual server use** - automation typically violates server rules and can result in bans. Created as a technical demonstration. Use at your own risk.

---

*Built by [@GibsonHF](https://github.com/GibsonHF)*
