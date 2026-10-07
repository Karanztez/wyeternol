import urllib.request
import json

url = "https://api.github.com/repos/Karanztez/wyeternol/actions/runs/37629327272/jobs"
req = urllib.request.Request(url, headers={"User-Agent": "Mozilla/5.0"})
try:
    with urllib.request.urlopen(req) as resp:
        data = json.loads(resp.read().decode())
        for j in data.get("jobs", []):
            print(f"Job: {j.get('name')}, Conclusion: {j.get('conclusion')}")
            for step in j.get("steps", []):
                print(f"  Step: {step.get('name')}, Status: {step.get('status')}, Conclusion: {step.get('conclusion')}")
except Exception as e:
    print(f"Error: {e}")
