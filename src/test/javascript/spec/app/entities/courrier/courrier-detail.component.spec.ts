/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierDetailComponent } from 'app/entities/courrier/courrier-detail.component';
import { Courrier } from 'app/shared/model/courrier.model';

describe('Component Tests', () => {
  describe('Courrier Management Detail Component', () => {
    let comp: CourrierDetailComponent;
    let fixture: ComponentFixture<CourrierDetailComponent>;
    const route = ({ data: of({ courrier: new Courrier(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CourrierDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CourrierDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.courrier).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
