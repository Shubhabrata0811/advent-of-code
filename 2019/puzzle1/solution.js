import fs from "node:fs";

const getRequiredFuel = (input) => {
  const ipArray = input.split("\n");
  return ipArray.reduce((reqFuel, mass) => {
    const fuel = Math.floor(Number(mass) / 3) - 2;
    return reqFuel + fuel;
  }, 0);
};

const ipContent = fs.readFileSync("./probleminput.txt", "utf8");
console.log("Fuel required: ", getRequiredFuel(ipContent));