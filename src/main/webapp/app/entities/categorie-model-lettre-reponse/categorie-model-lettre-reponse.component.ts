import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { AccountService } from 'app/core';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse',
  templateUrl: './categorie-model-lettre-reponse.component.html'
})
export class CategorieModelLettreReponseComponent implements OnInit, OnDestroy {
  categorieModelLettreReponses: ICategorieModelLettreReponse[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.categorieModelLettreReponseService
      .query()
      .pipe(
        filter((res: HttpResponse<ICategorieModelLettreReponse[]>) => res.ok),
        map((res: HttpResponse<ICategorieModelLettreReponse[]>) => res.body)
      )
      .subscribe(
        (res: ICategorieModelLettreReponse[]) => {
          this.categorieModelLettreReponses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCategorieModelLettreReponses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICategorieModelLettreReponse) {
    return item.id;
  }

  registerChangeInCategorieModelLettreReponses() {
    this.eventSubscriber = this.eventManager.subscribe('categorieModelLettreReponseListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
