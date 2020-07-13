import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVoie } from 'app/shared/model/voie.model';

type EntityResponseType = HttpResponse<IVoie>;
type EntityArrayResponseType = HttpResponse<IVoie[]>;

@Injectable({ providedIn: 'root' })
export class VoieService {
  public resourceUrl = SERVER_API_URL + 'api/voies';

  constructor(protected http: HttpClient) {}

  create(voie: IVoie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voie);
    return this.http
      .post<IVoie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(voie: IVoie): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(voie);
    return this.http
      .put<IVoie>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVoie>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVoie[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(voie: IVoie): IVoie {
    const copy: IVoie = Object.assign({}, voie, {
      createdAt: voie.createdAt != null && voie.createdAt.isValid() ? voie.createdAt.toJSON() : null,
      updatedAt: voie.updatedAt != null && voie.updatedAt.isValid() ? voie.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((voie: IVoie) => {
        voie.createdAt = voie.createdAt != null ? moment(voie.createdAt) : null;
        voie.updatedAt = voie.updatedAt != null ? moment(voie.updatedAt) : null;
      });
    }
    return res;
  }
}
