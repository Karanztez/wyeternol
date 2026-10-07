import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.event.server.ServerListPingEvent"], capture_output=True, text=True)
print("ServerListPingEvent:")
print(res.stdout)

res2 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.ping.Status"], capture_output=True, text=True)
print("Status:")
print(res2.stdout)

res3 = subprocess.run(["javap", "-cp", jar, "net.minestom.server.ping.Status$Builder"], capture_output=True, text=True)
print("Status$Builder:")
print(res3.stdout)
