import fs from "fs";

const part1 = (filePath) => {
  const directionArray = fs.readFileSync(filePath, "utf-8").split(", ");
  let x = 0;
  let y = 0;
  let face = 0;

  directionArray.forEach((direction) => {
    const turn = direction[0];
    const steps = Number(direction.slice(1));

    face = (turn === "R" ? face + 1 : face + 3) % 4;

    switch (face) {
      case 0:
        y += steps;
        break;
      case 1:
        x += steps;
        break;
      case 2:
        y -= steps;
        break;
      case 3:
        x -= steps;
        break;
    }
  });

  console.log("Part 1: ", Math.abs(x) + Math.abs(y));
};

part1("problemIP.txt");


const part2 = (filePath) => {
  const directionArray = fs.readFileSync(filePath, "utf-8").split(", ");
  let x = 0;
  let y = 0;
  let face = 0;
  const visitedBlockArr = [];

  for (let direction of directionArray) {
    const turn = direction[0];
    const steps = Number(direction.slice(1));

    face = (turn === "R" ? face + 1 : face + 3) % 4;

    for (let move = 0; move < steps; move++) {
      switch (face) {
        case 0:
          y++;
          break;
        case 1:
          x++;
          break;
        case 2:
          y--;
          break;
        case 3:
          x--;
          break;
      }

      if (visitedBlockArr.includes(`${x},${y}`)) {
        console.log("Part 2: ", Math.abs(x) + Math.abs(y));
        return;
      }

      visitedBlockArr.push(`${x},${y}`);
    }
  }
}

part2("problemIP.txt");
