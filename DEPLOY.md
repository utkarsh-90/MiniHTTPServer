# Deploy to Render

[Render](https://render.com) free tier. Uses the repo Dockerfile.

---

## Steps

1. **Push your code to GitHub** (if you haven’t already)
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin https://github.com/YOUR_USERNAME/MiniJavaServer.git
   git push -u origin main
   ```

2. **Sign up** at [render.com](https://render.com) and log in with GitHub.

3. **Create a Web Service**
   - **New** → **Web Service**
   - Connect your **MiniJavaServer** repo
   - **Environment**: **Docker** (Render will use your Dockerfile)
   - **Instance type**: **Free**

4. **Deploy**
   - Click **Create Web Service**
   - Render builds the image and runs it. You’ll get a URL like `https://minijava-server.onrender.com`.

5. **Test**
   ```bash
   curl https://YOUR-APP-NAME.onrender.com/hello
   ```

App reads `PORT` from the environment.

**Note:** Free tier spins down after ~15 min idle; first request may take 30–60s.

---

## Troubleshooting

| Issue | What to do |
|-------|------------|
| Build fails | Ensure the repo has `Dockerfile`, `build.gradle.kts`, `settings.gradle.kts`, and `src/` at the root. |
| 503 / connection refused | Wait 1–2 minutes after deploy, or after a cold start. Check **Logs** in the Render dashboard. |
| Port error | The app reads `PORT` from the environment; don’t set it manually. |
