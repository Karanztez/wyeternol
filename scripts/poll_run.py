import urllib.request
import json
import time
import sys

run_id = "37630309176"
url = f"https://api.github.com/repos/Karanztez/wyeternol/actions/runs/{run_id}"

for _ in range(12):
    time.sleep(5)
    req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
    try:
        with urllib.request.urlopen(req) as resp:
            data = json.loads(resp.read().decode())
            status = data.get("status")
            conclusion = data.get("conclusion")
            print(f"Run {run_id}: status={status}, conclusion={conclusion}")
            if status == "completed":
                sys.exit(0 if conclusion == "success" else 1)
    except Exception as e:
        print(f"Error: {e}")
