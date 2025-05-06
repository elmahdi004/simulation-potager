<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-green-700 text-center">
      Liste des Plantes
    </h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div
        v-for="plant in plants"
        :key="plant.id"
        class="bg-white rounded-xl shadow-lg p-6 flex flex-col items-center hover:scale-105 transition-transform"
      >
        <div
          class="w-16 h-16 bg-green-200 rounded-full flex items-center justify-center mb-4"
        >
          <span class="text-2xl font-bold text-green-700">🌱</span>
        </div>
        <h3 class="text-xl font-semibold text-gray-800 mb-2">
          {{ plant.species }}
        </h3>
        <p class="text-gray-600">
          Âge : <span class="font-medium">{{ plant.age }}</span>
        </p>
        <p class="text-gray-600">
          Maturité :
          <span
            :class="
              plant.isMature
                ? 'text-green-600 font-semibold'
                : 'text-yellow-500 font-semibold'
            "
          >
            {{ plant.isMature ? "Oui" : "Non" }}
          </span>
        </p>
      </div>
    </div>
    <div v-if="plants.length === 0" class="text-center text-gray-500 mt-10">
      Aucune plante à afficher.
    </div>
  </div>
</template>

<script>
export default {
  name: "PlantList",
  data() {
    return {
      plants: [
        { id: 1, species: "Tomate", age: 3, isMature: false },
        { id: 2, species: "Carotte", age: 5, isMature: true },
        { id: 3, species: "Laitue", age: 2, isMature: false },
        { id: 4, species: "Aubergine", age: 6, isMature: true },
      ],
    };
  },
};
</script>

<style scoped></style>
