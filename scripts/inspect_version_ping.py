import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.MinecraftConstants"], capture_output=True, text=True)
print("MinecraftConstants:")
print(res.stdout)

res2 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.ping.ResponseData"], capture_output=True, text=True)
print("ResponseData:")
print(res2.stdout)
