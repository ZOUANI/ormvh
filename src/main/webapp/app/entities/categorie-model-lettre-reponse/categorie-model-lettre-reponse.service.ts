import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

type EntityResponseType = HttpResponse<ICategorieModelLettreReponse>;
type EntityArrayResponseType = HttpResponse<ICategorieModelLettreReponse[]>;

@Injectable({ providedIn: 'root' })
export class CategorieModelLettreReponseService {
  public resourceUrl = SERVER_API_URL + 'api/categorie-model-lettre-reponses';

  constructor(protected http: HttpClient) {}

  create(categorieModelLettreReponse: ICategorieModelLettreReponse): Observable<EntityResponseType> {
    return this.http.post<ICategorieModelLettreReponse>(this.resourceUrl, categorieModelLettreReponse, { observe: 'response' });
  }

  update(categorieModelLettreReponse: ICategorieModelLettreReponse): Observable<EntityResponseType> {
    return this.http.put<ICategorieModelLettreReponse>(this.resourceUrl, categorieModelLettreReponse, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICategorieModelLettreReponse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICategorieModelLettreReponse[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
