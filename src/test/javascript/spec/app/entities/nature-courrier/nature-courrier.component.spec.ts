import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { NatureCourrierComponent } from 'app/entities/nature-courrier/nature-courrier.component';
import { NatureCourrierService } from 'app/entities/nature-courrier/nature-courrier.service';
import { NatureCourrier } from 'app/shared/model/nature-courrier.model';

describe('Component Tests', () => {
  describe('NatureCourrier Management Component', () => {
    let comp: NatureCourrierComponent;
    let fixture: ComponentFixture<NatureCourrierComponent>;
    let service: NatureCourrierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [NatureCourrierComponent],
      })
        .overrideTemplate(NatureCourrierComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NatureCourrierComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NatureCourrierService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new NatureCourrier(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.natureCourriers && comp.natureCourriers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
