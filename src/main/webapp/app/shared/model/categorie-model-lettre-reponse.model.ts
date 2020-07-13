export interface ICategorieModelLettreReponse {
  id?: number;
  libelle?: string;
}

export class CategorieModelLettreReponse implements ICategorieModelLettreReponse {
  constructor(public id?: number, public libelle?: string) {}
}
