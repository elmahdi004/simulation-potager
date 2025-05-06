<template>
  <div
    class="max-w-md mx-auto mt-10 bg-white rounded-xl shadow-lg p-8 flex flex-col items-center"
  >
    <div
      class="w-20 h-20 bg-green-200 rounded-full flex items-center justify-center mb-6"
    >
      <span class="text-4xl">🌱</span>
    </div>
    <h2 class="text-2xl font-bold text-green-700 mb-2">{{ plant.species }}</h2>
    <div class="text-gray-700 w-full">
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Âge :</span>
        <span>{{ plant.age }} jours</span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Maturité :</span>
        <span
          :class="
            plant.isMature
              ? 'text-green-600 font-semibold'
              : 'text-yellow-500 font-semibold'
          "
        >
          {{ plant.isMature ? "Oui" : "Non" }}
        </span>
      </div>
      <div class="flex justify-between py-2 border-b">
        <span class="font-semibold">Parcelle :</span>
        <span>{{ plant.plot }}</span>
      </div>
      <div class="flex justify-between py-2">
        <span class="font-semibold">Espèce :</span>
        <span>{{ plant.species }}</span>
      </div>
    </div>
    <div class="mt-6 w-full">
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  name: "PlantDetail",
  props: {
    plant: {
      type: Object,
      default: () => ({
        id: 1,
        species: "Tomate",
        age: 3,
        isMature: false,
        plot: "A1",
      }),
    },
  },
};
</script>

<style scoped></style>
