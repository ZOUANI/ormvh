import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';

@Component({
  selector: 'jhi-nature-courrier-delete-dialog',
  templateUrl: './nature-courrier-delete-dialog.component.html'
})
export class NatureCourrierDeleteDialogComponent {
  natureCourrier: INatureCourrier;

  constructor(
    protected natureCourrierService: NatureCourrierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.natureCourrierService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'natureCourrierListModification',
        content: 'Deleted an natureCourrier'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-nature-courrier-delete-popup',
  template: ''
})
export class NatureCourrierDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ natureCourrier }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NatureCourrierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.natureCourrier = natureCourrier;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/nature-courrier', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/nature-courrier', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
