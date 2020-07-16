import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';
import { CategorieModelLettreReponseDeleteDialogComponent } from './categorie-model-lettre-reponse-delete-dialog.component';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse',
  templateUrl: './categorie-model-lettre-reponse.component.html',
})
export class CategorieModelLettreReponseComponent implements OnInit, OnDestroy {
  categorieModelLettreReponses?: ICategorieModelLettreReponse[];
  eventSubscriber?: Subscription;

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.categorieModelLettreReponseService
      .query()
      .subscribe((res: HttpResponse<ICategorieModelLettreReponse[]>) => (this.categorieModelLettreReponses = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCategorieModelLettreReponses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICategorieModelLettreReponse): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCategorieModelLettreReponses(): void {
    this.eventSubscriber = this.eventManager.subscribe('categorieModelLettreReponseListModification', () => this.loadAll());
  }

  delete(categorieModelLettreReponse: ICategorieModelLettreReponse): void {
    const modalRef = this.modalService.open(CategorieModelLettreReponseDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.categorieModelLettreReponse = categorieModelLettreReponse;
  }
}
