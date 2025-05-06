<template>
  <div class="container mx-auto py-8">
    <h2 class="text-3xl font-bold mb-6 text-blue-700 text-center">
      Liste des Insectes
    </h2>
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
      <div
        v-for="insect in insects"
        :key="insect.id"
        class="bg-white rounded-xl shadow-lg p-6 flex flex-col items-center hover:scale-105 transition-transform"
      >
        <div
          class="w-16 h-16 bg-blue-200 rounded-full flex items-center justify-center mb-4"
        >
          <span class="text-2xl font-bold text-blue-700">🐞</span>
        </div>
        <h3 class="text-xl font-semibold text-gray-800 mb-2">
          {{ insect.species }}
        </h3>
        <p class="text-gray-600">
          Sexe : <span class="font-medium">{{ insect.sex }}</span>
        </p>
        <p class="text-gray-600">
          Indice de santé :
          <span
            :class="insect.healthIndex > 5 ? 'text-green-600' : 'text-red-500'"
            >{{ insect.healthIndex }}</span
          >
        </p>
        <p class="text-gray-600">
          Mobilité : <span class="font-medium">{{ insect.mobility }}</span>
        </p>
        <p class="text-gray-600">
          Résistance : <span class="font-medium">{{ insect.resistance }}</span>
        </p>
      </div>
    </div>
    <div v-if="insects.length === 0" class="text-center text-gray-500 mt-10">
      Aucun insecte à afficher.
    </div>
  </div>
</template>

<script>
export default {
  name: "InsectList",
  data() {
    return {
      insects: [
        {
          id: 1,
          species: "Coccinelle",
          sex: "Femelle",
          healthIndex: 8,
          mobility: "Haute",
          resistance: "Forte",
        },
        {
          id: 2,
          species: "Puceron",
          sex: "Mâle",
          healthIndex: 4,
          mobility: "Moyenne",
          resistance: "Faible",
        },
        {
          id: 3,
          species: "Papillon",
          sex: "Femelle",
          healthIndex: 7,
          mobility: "Haute",
          resistance: "Moyenne",
        },
        {
          id: 4,
          species: "Ver",
          sex: "Mâle",
          healthIndex: 6,
          mobility: "Faible",
          resistance: "Forte",
        },
      ],
    };
  },
};
</script>

<style scoped></style>
