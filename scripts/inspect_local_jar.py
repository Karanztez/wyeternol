import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "-constants", "net.minestom.server.MinecraftConstants"], capture_output=True, text=True)
print(res.stdout)
