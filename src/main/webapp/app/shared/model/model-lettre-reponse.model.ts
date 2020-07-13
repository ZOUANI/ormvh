import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

export interface IModelLettreReponse {
  id?: number;
  libelle?: string;
  code?: string;
  chemin?: string;
  categorieModelLettreReponse?: ICategorieModelLettreReponse;
}

export class ModelLettreReponse implements IModelLettreReponse {
  constructor(
    public id?: number,
    public libelle?: string,
    public code?: string,
    public chemin?: string,
    public categorieModelLettreReponse?: ICategorieModelLettreReponse
  ) {}
}
