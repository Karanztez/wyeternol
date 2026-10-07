import subprocess

jar = "build/wyeternol-server.jar"
res = subprocess.run(["javap", "-cp", jar, "net.minestom.server.item.ItemStack$Builder"], capture_output=True, text=True)
for line in res.stdout.splitlines():
    if "custom" in line.lower() or "name" in line.lower() or "lore" in line.lower() or "build" in line.lower():
        print(line)
