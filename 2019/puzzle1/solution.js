import fs from "node: fs";

const recursiveFuelRequirement = (mass) => {
  const fuel = Math.floor(Number(mass) / 3) - 2;
  return fuel <= 0 ? 0 : fuel + recursiveFuelRequirement(fuel);
};

const getRequiredFuel = (input) => {
  const ipArray = input.split("\n");
  return ipArray.reduce((reqFuel, mass) => {
    return reqFuel + recursiveFuelRequirement(mass);
  }, 0);
};


const ipContent = fs.readFileSync("./probleminput.txt", "utf8");
console.log("Fuel required: ", getRequiredFuel(ipContent));