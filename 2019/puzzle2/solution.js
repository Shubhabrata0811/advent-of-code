import fs from "node:fs";

class IntCodeComputer {
  constructor(program) {
    this.program = program;
    this.ip = 0;
  }

  execute() {
    while (this.program[this.ip] !== 99) {
      const [opcode, arg1, arg2, arg3] = this.program.slice(
        this.ip,
        this.ip + 4
      );
      switch (opcode) {
        case 1:
          this.program[arg3] = this.program[arg1] + this.program[arg2];
          break;
        case 2:
          this.program[arg3] = this.program[arg1] * this.program[arg2];
          break;
        default:
          throw new Error("Invalid opcode");
      }
      this.ip += 4;
    }
    return this.program;
  }
}

const ipContent = fs
  .readFileSync("./problemIP.txt", "utf8")
  .split(",")
  .map(Number);

//part 2
const targetOutput = 19690720;
for (let noun = 0; noun < 100; noun++) {
  for (let verb = 0; verb < 100; verb++) {
    const program = [...ipContent];
    program[1] = noun;
    program[2] = verb;
    const computerInstance = new IntCodeComputer(program);
    if (computerInstance.execute()[0] === targetOutput) {
      console.log("100 * noun + verb:", 100 * noun + verb);
      break;
    }
  }
}
