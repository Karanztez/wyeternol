import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.instance.LightingChunk"], capture_output=True, text=True)
print(res.stdout)
