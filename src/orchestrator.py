import subprocess
import argparse

def compile_smc():
    """Runs the SMC compilation script."""
    print("Running SMC compilation...")
    subprocess.run(['bash', 'workflows/compile_smc.sh'], check=True)

def main():
    """Main entry point for the orchestrator."""
    parser = argparse.ArgumentParser(description="SMC Bot Orchestrator")
    parser.add_argument('bot_name', choices=['react', 'kotlin'], help="The name of the bot to interact with.")
    parser.add_argument('event', choices=['startTask', 'taskComplete', 'taskFailed', 'reset'], help="The event to send to the bot.")
    parser.add_argument('--task', help="The task to start.")

    args = parser.parse_args()

    print(f"Sending event '{args.event}' to bot '{args.bot_name}'")

    if args.event == 'startTask':
        if not args.task:
            print("Error: --task is required for the startTask event.")
            return
        print(f"Starting task: {args.task}")

    print("Orchestration complete.")

if __name__ == '__main__':
    compile_smc()
    main()