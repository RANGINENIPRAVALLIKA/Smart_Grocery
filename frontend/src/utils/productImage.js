const PLACEHOLDER_RE = /placehold|product|grocery/i;

const NAME_IMAGE_MAP = {
  'coca cola': 'https://source.unsplash.com/featured/800x600?coca%20cola%20can',
  pepsi: 'https://source.unsplash.com/featured/800x600?pepsi%20bottle',
  frooti: 'https://source.unsplash.com/featured/800x600?frooti%20juice%20pack',
  'real juice': 'https://source.unsplash.com/featured/800x600?juice%20carton',
  'amul milk': 'https://source.unsplash.com/featured/800x600?milk%20packet',
  curd: 'https://source.unsplash.com/featured/800x600?curd%20bowl',
  butter: 'https://source.unsplash.com/featured/800x600?butter%20block',
  'cheese slice': 'https://source.unsplash.com/featured/800x600?cheese%20slices',
  lays: 'https://source.unsplash.com/featured/800x600?lays%20chips%20packet',
  kurkure: 'https://source.unsplash.com/featured/800x600?kurkure%20snack%20packet',
  'bingo chips': 'https://source.unsplash.com/featured/800x600?bingo%20chips%20packet',
  'parle g': 'https://source.unsplash.com/featured/800x600?parle%20g%20biscuit%20packet',
  banana: 'https://source.unsplash.com/featured/800x600?banana%20fruit',
  apple: 'https://source.unsplash.com/featured/800x600?apple%20fruit',
  orange: 'https://source.unsplash.com/featured/800x600?orange%20fruit',
  mango: 'https://source.unsplash.com/featured/800x600?mango%20fruit',
  carrot: 'https://source.unsplash.com/featured/800x600?carrot%20vegetable',
  capsicum: 'https://source.unsplash.com/featured/800x600?capsicum%20vegetable',
  cauliflower: 'https://source.unsplash.com/featured/800x600?cauliflower%20vegetable',
  cabbage: 'https://source.unsplash.com/featured/800x600?cabbage%20vegetable',
};

const CATEGORY_IMAGE_POOLS = {
  fruits: [
    'https://source.unsplash.com/featured/800x600?fresh%20fruit%20basket',
    'https://source.unsplash.com/featured/800x600?apple%20orange%20banana',
    'https://source.unsplash.com/featured/800x600?mixed%20fruits',
  ],
  vegetables: [
    'https://source.unsplash.com/featured/800x600?fresh%20vegetables',
    'https://source.unsplash.com/featured/800x600?broccoli%20carrot%20leafy%20greens',
    'https://source.unsplash.com/featured/800x600?vegetable%20crate',
  ],
  dairy: [
    'https://source.unsplash.com/featured/800x600?milk%20bottle',
    'https://source.unsplash.com/featured/800x600?cheese%20slices',
    'https://source.unsplash.com/featured/800x600?curd%20bowl',
  ],
  beverages: [
    'https://source.unsplash.com/featured/800x600?juice%20bottle',
    'https://source.unsplash.com/featured/800x600?cold%20drink%20can',
    'https://source.unsplash.com/featured/800x600?refreshing%20beverage',
  ],
  'tea & coffee': [
    'https://source.unsplash.com/featured/800x600?tea%20pack',
    'https://source.unsplash.com/featured/800x600?coffee%20beans',
    'https://source.unsplash.com/featured/800x600?tea%20cup',
  ],
  snacks: [
    'https://source.unsplash.com/featured/800x600?chips%20packet',
    'https://source.unsplash.com/featured/800x600?snack%20pack',
    'https://source.unsplash.com/featured/800x600?crispy%20snacks',
  ],
  'biscuits & cookies': [
    'https://source.unsplash.com/featured/800x600?cookies%20packet',
    'https://source.unsplash.com/featured/800x600?biscuit%20packet',
    'https://source.unsplash.com/featured/800x600?bakery%20snacks',
  ],
  'cold drinks': [
    'https://source.unsplash.com/featured/800x600?cold%20drinks',
    'https://source.unsplash.com/featured/800x600?soft%20drink%20bottle',
    'https://source.unsplash.com/featured/800x600?juice%20cold%20drink',
  ],
  'frozen foods': [
    'https://source.unsplash.com/featured/800x600?frozen%20food',
    'https://source.unsplash.com/featured/800x600?frozen%20vegetables',
    'https://source.unsplash.com/featured/800x600?ice%20cream%20frozen',
  ],
  'packaged foods': [
    'https://source.unsplash.com/featured/800x600?grocery%20pack',
    'https://source.unsplash.com/featured/800x600?pantry%20staples',
    'https://source.unsplash.com/featured/800x600?packed%20food%20basket',
  ],
  default: [
    'https://source.unsplash.com/featured/800x600?fresh%20grocery%20basket',
    'https://source.unsplash.com/featured/800x600?grocery%20essentials',
    'https://source.unsplash.com/featured/800x600?fresh%20produce%20market',
  ],
};

function normalizeText(value) {
  return String(value || '')
    .toLowerCase()
    .replace(/[^a-z0-9]+/g, ' ')
    .trim();
}

function hashString(value) {
  let hash = 0;
  for (let i = 0; i < value.length; i += 1) {
    hash = (hash * 31 + value.charCodeAt(i)) % 1000003;
  }
  return hash;
}

export function getProductImage(product) {
  const rawImage = product?.imageUrl;

  if (typeof rawImage === 'string' && rawImage.trim() && !PLACEHOLDER_RE.test(rawImage)) {
    return rawImage;
  }

  const name = normalizeText(product?.name || '');
  const category = normalizeText(product?.category || '');

  const directMatch = Object.entries(NAME_IMAGE_MAP).find(([key]) => name.includes(key) || key.includes(name));
  if (directMatch) {
    return directMatch[1];
  }

  const pool = CATEGORY_IMAGE_POOLS[category] || CATEGORY_IMAGE_POOLS.default;
  const index = hashString(name || category || 'product') % pool.length;
  return pool[index];
}
