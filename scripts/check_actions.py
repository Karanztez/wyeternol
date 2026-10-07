import urllib.request
import json

url = "https://api.github.com/repos/Karanztez/wyeternol/actions/runs"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req) as resp:
        data = json.loads(resp.read().decode())
        runs = data.get("workflow_runs", [])
        print(f"Total runs: {len(runs)}")
        for r in runs[:3]:
            print(f"ID: {r.get('id')}, Status: {r.get('status')}, Conclusion: {r.get('conclusion')}, Name: {r.get('name')}")
except Exception as e:
    print(f"Error: {e}")
